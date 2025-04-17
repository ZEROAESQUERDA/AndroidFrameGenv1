package com.mobfgsr.framegeneration

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mobfgsr.framegeneration.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var pagerAdapter: SettingsPagerAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Inicializa a biblioteca nativa
        System.loadLibrary("mobfgsr")
        
        // Configura a toolbar
        setSupportActionBar(binding.toolbar)
        
        // Configura o ViewPager e TabLayout
        setupViewPager()
        
        // Configura o botão Aplicar
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Garante que o botão Aplicar esteja visível em ambas as abas
                if (position == 0) {
                    val basicFragment = pagerAdapter.getBasicSettingsFragment()
                    basicFragment.view?.findViewById<android.widget.Button>(R.id.applyButton)?.setOnClickListener {
                        applySettings()
                    }
                }
            }
        })
    }
    
    private fun setupViewPager() {
        pagerAdapter = SettingsPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter
        
        // Conecta o TabLayout com o ViewPager2
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_basic)
                1 -> getString(R.string.tab_advanced)
                else -> null
            }
        }.attach()
    }
    
    private fun applySettings() {
        // Salva as configurações de ambos os fragmentos
        val basicFragment = pagerAdapter.getBasicSettingsFragment()
        val advancedFragment = pagerAdapter.getAdvancedSettingsFragment()
        
        basicFragment.saveSettings()
        advancedFragment.saveSettings()
        
        // Obtém as configurações
        val basicSettings = basicFragment.getBasicSettings()
        val advancedSettings = advancedFragment.getAdvancedSettings()
        
        // Verifica se o pacote do jogo foi especificado
        val gamePackage = basicSettings["gamePackage"] as String
        if (gamePackage.isBlank()) {
            Toast.makeText(this, "Por favor, especifique o pacote do jogo", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Chama o método nativo para aplicar as configurações
        val result = applyFrameGenerationSettings(
            gamePackage,
            basicSettings["enableFrameGeneration"] as Boolean,
            basicSettings["enableSuperResolution"] as Boolean,
            basicSettings["generatedFramesCount"] as Int,
            advancedSettings["depthDiffThresholdSR"] as Float,
            advancedSettings["colorDiffThresholdFG"] as Float,
            advancedSettings["depthDiffThresholdFG"] as Float,
            advancedSettings["depthScale"] as Float,
            advancedSettings["depthBias"] as Float,
            advancedSettings["renderWidth"] as Int,
            advancedSettings["renderHeight"] as Int,
            advancedSettings["upsampleScale"] as Float
        )
        
        if (result) {
            Toast.makeText(this, "Configurações aplicadas com sucesso para $gamePackage!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Erro ao aplicar configurações", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onPause() {
        super.onPause()
        
        // Salva as configurações ao pausar a atividade
        val basicFragment = pagerAdapter.getBasicSettingsFragment()
        val advancedFragment = pagerAdapter.getAdvancedSettingsFragment()
        
        basicFragment.saveSettings()
        advancedFragment.saveSettings()
    }
    
    // Métodos nativos
    private external fun applyFrameGenerationSettings(
        gamePackage: String,
        enableFrameGeneration: Boolean,
        enableSuperResolution: Boolean,
        generatedFramesCount: Int,
        depthDiffThresholdSR: Float,
        colorDiffThresholdFG: Float,
        depthDiffThresholdFG: Float,
        depthScale: Float,
        depthBias: Float,
        renderWidth: Int,
        renderHeight: Int,
        upsampleScale: Float
    ): Boolean
    
    companion object {
        init {
            System.loadLibrary("mobfgsr")
        }
    }
}
