package com.mobfgsr.framegeneration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.mobfgsr.framegeneration.databinding.FragmentBasicSettingsBinding

class BasicSettingsFragment : Fragment() {

    private var _binding: FragmentBasicSettingsBinding? = null
    private val binding get() = _binding!!

    // Configurações básicas
    private var enableFrameGeneration = true
    private var enableSuperResolution = false
    private var generatedFramesCount = 1
    private var gamePackage = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBasicSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Carrega as configurações salvas
        loadSettings()
        
        // Configura os controles com os valores iniciais
        setupControls()
        
        // Configura os listeners
        setupListeners()
    }

    private fun loadSettings() {
        val prefs = requireActivity().getSharedPreferences("FrameGenPrefs", 0)
        enableFrameGeneration = prefs.getBoolean("enableFrameGeneration", true)
        enableSuperResolution = prefs.getBoolean("enableSuperResolution", false)
        generatedFramesCount = prefs.getInt("generatedFramesCount", 1)
        gamePackage = prefs.getString("gamePackage", "") ?: ""
    }

    private fun setupControls() {
        // Configura os switches
        binding.frameGenerationSwitch.isChecked = enableFrameGeneration
        binding.superResolutionSwitch.isChecked = enableSuperResolution
        
        // Configura o SeekBar
        binding.framesCountSeekBar.progress = generatedFramesCount - 1 // 0-based index
        binding.framesCountValueText.text = generatedFramesCount.toString()
        
        // Configura o campo de texto do pacote do jogo
        binding.gamePackageEditText.setText(gamePackage)
        
        // Atualiza o estado dos controles
        updateControlsState()
    }

    private fun setupListeners() {
        // Listener para o switch de Frame Generation
        binding.frameGenerationSwitch.setOnCheckedChangeListener { _, isChecked ->
            enableFrameGeneration = isChecked
            updateControlsState()
        }
        
        // Listener para o switch de Super Resolution
        binding.superResolutionSwitch.setOnCheckedChangeListener { _, isChecked ->
            enableSuperResolution = isChecked
        }
        
        // Listener para o SeekBar de contagem de frames
        binding.framesCountSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                generatedFramesCount = progress + 1 // Converte 0-based para 1-based
                binding.framesCountValueText.text = generatedFramesCount.toString()
            }
            
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        
        // Listener para o botão Resetar
        binding.resetButton.setOnClickListener {
            resetSettings()
        }
    }

    private fun updateControlsState() {
        val enabled = binding.frameGenerationSwitch.isChecked
        binding.superResolutionSwitch.isEnabled = enabled
        binding.framesCountSeekBar.isEnabled = enabled
        binding.framesCountLabel.alpha = if (enabled) 1.0f else 0.5f
        binding.framesCountValueText.alpha = if (enabled) 1.0f else 0.5f
    }

    fun saveSettings() {
        // Atualiza o pacote do jogo
        gamePackage = binding.gamePackageEditText.text.toString()
        
        // Salva as configurações
        val prefs = requireActivity().getSharedPreferences("FrameGenPrefs", 0)
        prefs.edit().apply {
            putBoolean("enableFrameGeneration", enableFrameGeneration)
            putBoolean("enableSuperResolution", enableSuperResolution)
            putInt("generatedFramesCount", generatedFramesCount)
            putString("gamePackage", gamePackage)
            apply()
        }
    }

    fun resetSettings() {
        // Restaura as configurações padrão
        enableFrameGeneration = true
        enableSuperResolution = false
        generatedFramesCount = 1
        
        // Atualiza a UI
        binding.frameGenerationSwitch.isChecked = enableFrameGeneration
        binding.superResolutionSwitch.isChecked = enableSuperResolution
        binding.framesCountSeekBar.progress = generatedFramesCount - 1
        binding.framesCountValueText.text = generatedFramesCount.toString()
        
        updateControlsState()
    }

    fun getBasicSettings(): Map<String, Any> {
        return mapOf(
            "enableFrameGeneration" to enableFrameGeneration,
            "enableSuperResolution" to enableSuperResolution,
            "generatedFramesCount" to generatedFramesCount,
            "gamePackage" to gamePackage
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = BasicSettingsFragment()
    }
}
