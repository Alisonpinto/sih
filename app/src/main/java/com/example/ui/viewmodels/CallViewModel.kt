package com.example.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.models.AIDetectionResult
import com.example.data.models.CallRecord
import com.example.data.models.DetectionVerdict
import com.example.data.models.RiskLevel
import com.example.data.repository.MockRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface ActiveCallPhase {
  data object Connecting : ActiveCallPhase
  data object Scanning : ActiveCallPhase
  data class ResultReady(
    val verdict: DetectionVerdict,
    val confidence: Int,
    val riskLevel: RiskLevel,
    val voiceAuthenticity: Int,
    val aiProbability: Int
  ) : ActiveCallPhase
}

class CallViewModel : ViewModel() {

  // Search & Filter Call Logs
  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  private val _selectedFilter = MutableStateFlow<DetectionVerdict?>(null)
  val selectedFilter: StateFlow<DetectionVerdict?> = _selectedFilter.asStateFlow()

  val callLogs: StateFlow<List<CallRecord>> = combine(
    MockRepository.callLogs,
    _searchQuery,
    _selectedFilter
  ) { list, query, filter ->
    list.filter { call ->
      val matchesQuery = query.isBlank() ||
        call.callerName.contains(query, ignoreCase = true) ||
        call.phoneNumber.contains(query, ignoreCase = true)
      val matchesFilter = filter == null || call.verdict == filter
      matchesQuery && matchesFilter
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val selectedCall: StateFlow<CallRecord?> = MockRepository.selectedCall

  // Active Call Live Simulation State
  private val _activeCallerName = MutableStateFlow("Unknown Caller")
  val activeCallerName: StateFlow<String> = _activeCallerName.asStateFlow()

  private val _activePhoneNumber = MutableStateFlow("+91 88921 55670")
  val activePhoneNumber: StateFlow<String> = _activePhoneNumber.asStateFlow()

  private val _callSeconds = MutableStateFlow(0)
  val callSeconds: StateFlow<Int> = _callSeconds.asStateFlow()

  private val _callPhase = MutableStateFlow<ActiveCallPhase>(ActiveCallPhase.Scanning)
  val callPhase: StateFlow<ActiveCallPhase> = _callPhase.asStateFlow()

  private val _simulateAiCall = MutableStateFlow(true)
  val simulateAiCall: StateFlow<Boolean> = _simulateAiCall.asStateFlow()

  private var timerJob: Job? = null
  private var analysisJob: Job? = null

  init {
    // Select first call by default for AI results tab if none selected
    if (MockRepository.selectedCall.value == null && MockRepository.callLogs.value.isNotEmpty()) {
      MockRepository.selectCallForAiDetails(MockRepository.callLogs.value.first())
    }
  }

  fun onSearchQueryChange(q: String) { _searchQuery.value = q }
  fun onFilterSelect(filter: DetectionVerdict?) { _selectedFilter.value = filter }

  fun selectCall(call: CallRecord) {
    MockRepository.selectCallForAiDetails(call)
  }

  fun setSimulateAiCall(isAi: Boolean) {
    _simulateAiCall.value = isAi
  }

  fun startLiveCall(
    callerName: String = if (_simulateAiCall.value) "Unknown Number" else "Rahul Mehta",
    phoneNumber: String = if (_simulateAiCall.value) "+91 88921 55670" else "+91 98234 11223",
    forceAi: Boolean? = null
  ) {
    val isAi = forceAi ?: _simulateAiCall.value
    _simulateAiCall.value = isAi
    _activeCallerName.value = callerName
    _activePhoneNumber.value = phoneNumber
    _callSeconds.value = 0
    _callPhase.value = ActiveCallPhase.Scanning

    timerJob?.cancel()
    analysisJob?.cancel()

    // Timer loop
    timerJob = viewModelScope.launch {
      while (true) {
        delay(1000)
        _callSeconds.value += 1
      }
    }

    // 3-second AI analysis scan simulation
    analysisJob = viewModelScope.launch {
      delay(3000)
      if (isAi) {
        _callPhase.value = ActiveCallPhase.ResultReady(
          verdict = DetectionVerdict.AI_DETECTED,
          confidence = 94,
          riskLevel = RiskLevel.HIGH,
          voiceAuthenticity = 6,
          aiProbability = 94
        )
      } else {
        _callPhase.value = ActiveCallPhase.ResultReady(
          verdict = DetectionVerdict.GENUINE,
          confidence = 97,
          riskLevel = RiskLevel.LOW,
          voiceAuthenticity = 97,
          aiProbability = 3
        )
      }
    }
  }

  fun endLiveCall(onCallEnded: () -> Unit) {
    timerJob?.cancel()
    analysisJob?.cancel()

    val isAi = when (val phase = _callPhase.value) {
      is ActiveCallPhase.ResultReady -> phase.verdict == DetectionVerdict.AI_DETECTED
      else -> _simulateAiCall.value
    }

    val newCall = MockRepository.addCallRecord(
      callerName = _activeCallerName.value,
      phoneNumber = _activePhoneNumber.value,
      isAiDetected = isAi
    )

    MockRepository.selectCallForAiDetails(newCall)
    _callPhase.value = ActiveCallPhase.Scanning
    _callSeconds.value = 0
    onCallEnded()
  }
}
