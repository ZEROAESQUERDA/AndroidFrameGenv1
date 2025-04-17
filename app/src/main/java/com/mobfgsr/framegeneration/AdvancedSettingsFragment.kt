package com.mobfgsr.framegeneration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.mobfgsr.framegeneration.databinding.FragmentAdvancedSettingsBinding

class AdvancedSettingsFragment : Fragment() {

    private var _binding: FragmentAdvancedSettingsBinding? = null
    private val binding get() = _binding!!

    // Configurações avançadas
    private var depthDiffThresholdSR = 0.01f
    private var colorDiffThresholdFG = 0.01f
    private var depthDiffThresholdFG = 0.004f
    private var depthScale = 1.0f
    private var depthBias = 0.0f
    private var renderWidth = 1920
    private var renderHeight = 1080
    private var upsampleScale = 1.0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdvancedSettingsBinding.inflate(inflater, container, false)
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
        depthDiffThresholdSR = prefs.getFloat("depthDiffThresholdSR", 0.01f)
        colorDiffThresholdFG = prefs.getFloat("colorDiffThresholdFG", 0.01f)
        depthDiffThresholdFG = prefs.getFloat("depthDiffThresholdFG", 0.004f)
        depthScale = prefs.getFloat("depthScale", 1.0f)
        depthBias = prefs.getFloat("depthBias", 0.0f)
        renderWidth = prefs.getInt("renderWidth", 1920)
        renderHeight = prefs.getInt("renderHeight", 1080)
        upsampleScale = prefs.getFloat("upsampleScale", 1.0f)
    }

    private fun setupControls() {
        // Configura os SeekBars
        binding.depthDiffThresholdSRSeekBar.progress = (depthDiffThresholdSR * 1000).toInt()
        binding.colorDiffThresholdFGSeekBar.progress = (colorDiffThresholdFG * 1000).toInt()
        binding.depthDiffThresholdFGSeekBar.progress = (depthDiffThresholdFG * 1000).toInt()
        binding.depthScaleSeekBar.progress = (depthScale * 100).toInt()
        binding.depthBiasSeekBar.progress = (depthBias * 100).toInt()
        binding.upsampleScaleSeekBar.progress = (upsampleScale * 10).toInt()
        
        // Configura os valores de texto
        binding.depthDiffThresholdSRValue.text = depthDiffThresholdSR.toString()
        binding.colorDiffThresholdFGValue.text = colorDiffThresholdFG.toString()
        binding.depthDiffThresholdFGValue.text = depthDiffThresholdFG.toString()
        binding.depthScaleValue.text = depthScale.toString()
        binding.depthBiasValue.text = depthBias.toString()
        binding.upsampleScaleValue.text = upsampleScale.toString()
        
        // Configura os campos de texto
        binding.renderWidthEditText.setText(renderWidth.toString())
        binding.renderHeightEditText.setText(renderHeight.toString())
    }

    private fun setupListeners() {
        // Listener para o SeekBar de threshold de diferença de profundidade (SR)
        binding.depthDiffThresholdSRSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                depthDiffThresholdSR = progress / 1000f
                binding.depthDiffThresholdSRValue.text = String.format("%.3f", depthDiffThresholdSR)
            }
            
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        
        // Listener para o SeekBar de threshold de diferença de cor (FG)
        binding.colorDiffThresholdFGSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                colorDiffThresholdFG = progress / 1000f
                binding.colorDiffThresholdFGValue.text = String.format("%.3f", colorDiffThresholdFG)
            }
            
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        
        // Listener para o SeekBar de threshold de diferença de profundidade (FG)
        binding.depthDiffThresholdFGSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                depthDiffThresholdFG = progress / 1000f
                binding.depthDiffThresholdFGValue.text = String.format("%.3f", depthDiffThresholdFG)
            }
            
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        
        // Listener para o SeekBar de escala de profundidade
        binding.depthScaleSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                depthScale = progress / 100f
                binding.depthScaleValue.text = String.format("%.2f", depthScale)
            }
            
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        
        // Listener para o SeekBar de bias de profundidade
        binding.depthBiasSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                depthBias = progress / 100f
                binding.depthBiasValue.text = String.format("%.2f", depthBias)
            }
            
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        
        // Listener para o SeekBar de escala de upsampling
        binding.upsampleScaleSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                upsampleScale = progress / 10f
                binding.upsampleScaleValue.text = String.format("%.1f", upsampleScale)
            }
            
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    fun saveSettings() {
        // Atualiza os valores dos campos de texto
        try {
            renderWidth = binding.renderWidthEditText.text.toString().toInt()
            renderHeight = binding.renderHeightEditText.text.toString().toInt()
        } catch (e: NumberFormatException) {
            // Em caso de erro, mantém os valores padrão
        }
        
        // Salva as configurações
        val prefs = requireActivity().getSharedPreferences("FrameGenPrefs", 0)
        prefs.edit().apply {
            putFloat("depthDiffThresholdSR", depthDiffThresholdSR)
            putFloat("colorDiffThresholdFG", colorDiffThresholdFG)
            putFloat("depthDiffThresholdFG", depthDiffThresholdFG)
            putFloat("depthScale", depthScale)
            putFloat("depthBias", depthBias)
            putInt("renderWidth", renderWidth)
            putInt("renderHeight", renderHeight)
            putFloat("upsampleScale", upsampleScale)
            apply()
        }
    }

    fun getAdvancedSettings(): Map<String, Any> {
        return mapOf(
            "depthDiffThresholdSR" to depthDiffThresholdSR,
            "colorDiffThresholdFG" to colorDiffThresholdFG,
            "depthDiffThresholdFG" to depthDiffThresholdFG,
            "depthScale" to depthScale,
            "depthBias" to depthBias,
            "renderWidth" to renderWidth,
            "renderHeight" to renderHeight,
            "upsampleScale" to upsampleScale
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = AdvancedSettingsFragment()
    }
}
