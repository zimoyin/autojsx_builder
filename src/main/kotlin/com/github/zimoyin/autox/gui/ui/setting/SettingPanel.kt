package com.github.zimoyin.autox.gui.ui.setting

import com.github.zimoyin.autox.builder.log
import com.github.zimoyin.autox.gui.ApkBuilderPojo
import com.github.zimoyin.autox.gui.ui.IPanel
import com.github.zimoyin.autox.gui.ui.console.ConsoleManager
import com.github.zimoyin.autox.gui.ui.setting.handle.SettingDataHandle
import java.awt.Color
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.io.File
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter
import kotlin.system.exitProcess

/**
 *
 * @author : zimo
 * @date : 2024/08/28
 */
class SettingPanel(private val config: ApkBuilderPojo? = null) : IPanel(GridBagLayout()) {

    private val scmt = SettingComponent().apply {
        config?.configPath?.let {
            if (File(it).exists()) {
                this.configField.text = it
                return@apply
            }
        }
        init()
    }
    private var Y = 0
    private val c = GridBagConstraints().apply {
        fill = GridBagConstraints.HORIZONTAL
        insets = Insets(5, 5, 5, 5)
    }

    private fun getCentralizedAssets(): String? {
        return config?.assets?.let {
            if (it.size == 1) it.first() else config.centralizedAssets()
        }
    }

    private fun SettingComponent.init() {
        workDirField.text = config?.workDir ?: ""
        assetsField.text = getCentralizedAssets() ?: ""
        projectJsonField.text = (config?.projectJson ?: "").let {
            if (File(it).exists()) it else ""
        }
        templateApkPathField.text = (config?.templateApkPath ?: "").let {
            if (File(it).exists()) it else ""
        }
        splashImageTextField.text = (config?.startIconPath ?: "").let {
            if (File(it).exists()) it else ""
        }
        appIconTextField.text = (config?.iconPath ?: "").let {
            if (File(it).exists()) it else ""
        }
        signatureFileTextField.text = (config?.signatureFile ?: "").let {
            if (File(it).exists()) it else ""
        }
        signatureAliasField.text = config?.signatureAlias ?: ""
        signaturePasswordField.text = config?.signaturePassword ?: ""
    }

    override fun createPanel(panel: IPanel): JComponent {
        addLabel("根配置:")
        addField("配置文件:", scmt.configField)
        addJSeparator(button = 0)
        addJSeparator(top = 2, button = 15)
        addLabel("工作环境:")
        addFileChooserField(
            "project.json 路径:",
            scmt.projectJsonField,
            scmt.projectJsonButton,
            FileNameExtensionFilter("JSON File", "json")
        )
//        addField("assets 路径:", scmt.assetsField)
//        addField("工作路径:", scmt.workDirField)
        addFileChooserField(
            "assets 路径:",
            scmt.assetsField,
            JButton("选择文件夹"),
            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        )
        addFileChooserField(
            "工作路径:",
            scmt.workDirField,
            JButton("选择文件夹"),
            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        )
        addFileChooserField(
            "APK模板路径:",
            scmt.templateApkPathField,
            scmt.templateApkPathButton,
            FileNameExtensionFilter("APK File", "apk")
        )

        addJSeparator()
        addLabel("包名信息:")
        addField("应用名称:", scmt.appNameField)
        addField("包名:", scmt.packageNameField)
        addField("版本名称:", scmt.versionNameField)
        addField("版本号 (纯数字):", scmt.versionCodeField)

        c.gridwidth = 2
        addJSeparator()
        addLabel("选择 Lib:")
        addLabel("Terminal")
        addCheckBox(scmt.libTerminalCheckBox)
        addCheckBox(scmt.libJackpalAndroidTerm5SoCheckBox)
        addCheckBox(scmt.libJackpalTermexec2SoCheckBox)
        addJSeparator(Color(0xA9A9A9), 1, 1)
        addLabel("Open CV")
        addCheckBox(scmt.libOpencvCheckBox)
        addCheckBox(scmt.libOpencvJava4SoCheckBox)
        addJSeparator(Color(0xA9A9A9), 1, 1)
        addLabel("Paddle OCR")
        addCheckBox(scmt.libPaddleOcrCheckBox)
        addCheckBox(scmt.libcxxSharedSoCheckBox)
        addCheckBox(scmt.libPaddleLightApiSharedSoCheckBox)
        addCheckBox(scmt.libHiaiSoCheckBox)
        addCheckBox(scmt.libHiaiIrSoCheckBox)
        addCheckBox(scmt.libHiaiIrBuildSoCheckBox)
        addCheckBox(scmt.libNativeSoCheckBox)
        addJSeparator(Color(0xA9A9A9), 1, 1)
        addLabel("Google ML Kit OCR")
        addCheckBox(scmt.libGoogleORCCheckBox)
        addCheckBox(scmt.libMlkitGoogleOcrPipelineSoCheckBox)
        addJSeparator(Color(0xA9A9A9), 1, 1)
        addLabel("TESSERACT OCR")
        addCheckBox(scmt.libTesseractCheckBox)
        addCheckBox(scmt.libTesseractSoCheckBox)
        addCheckBox(scmt.libleptonicaSoCheckBox)
        addCheckBox(scmt.libPngSoCheckBox)
        addCheckBox(scmt.libJpegSoCheckBox)
        addJSeparator(Color(0xA9A9A9), 1, 1)
        addLabel("7zip")
        addCheckBox(scmt.lib7zipCheckBox)
        addCheckBox(scmt.libP7zipSoCheckBox)

        addJSeparator()
        addLabel("应用设置:")
        addCheckBox(scmt.displaySplashCheckBox)
        addCheckBox(scmt.hideDesktopIconCheckBox)
        addCheckBox(scmt.stableModeCheckBox)
        addCheckBox(scmt.hideLogCheckBox)
        addCheckBox(scmt.volumeKeyEndTaskCheckBox)

        addJSeparator()
        addLabel("权限设置:")
        addCheckBox(scmt.requestAccessibilityCheckBox)
        addCheckBox(scmt.removeAccessibilityCheckBox)
        addCheckBox(scmt.requestOverlayPermissionCheckBox)
        addCheckBox(scmt.requestBackgroundPopupPermissionCheckBox)

        addField("启动界面文本:", scmt.startTextTextField)
        addField("无障碍描述:", scmt.accessibilityDescriptionTextField)

        addJSeparator()
        addLabel("图标设置:")
        addFileChooserField(
            "开屏图片:",
            scmt.splashImageTextField,
            scmt.splashImageButton,
            FileNameExtensionFilter("PNG Image File", "png")
        )
        addFileChooserField(
            "应用图标:",
            scmt.appIconTextField,
            scmt.appIconButton,
            FileNameExtensionFilter("Image File", "png", "jpg", "jpeg", "ico", "icon")
        )

        addJSeparator()
        addLabel("签名设置:")
        addFileChooserField(
            "签名文件:",
            scmt.signatureFileTextField,
            scmt.signatureFileButton,
            FileNameExtensionFilter("Key File", "jks", "keystore", ".p12", ".pkcs12")
        )
        addField("签名别名:", scmt.signatureAliasField)
        addField("签名密码:", scmt.signaturePasswordField)

        addOkButton()
        addSaveButton()
        addOpenConsoleButton()
        addExitButton()
        val scrollPane = JScrollPane(panel).apply {
            verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
            horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
            verticalScrollBar.unitIncrement = 25
            verticalScrollBar.blockIncrement = 50
        }

        return scrollPane
    }

    private fun addOkButton(y: Int = Y++) {
        add(JButton("确定").apply {
            // 监听
            background = Color(0x375A81)
            addActionListener {
                log("开始生成APK")
                SettingDataHandle(scmt).handle()
            }
        }, c.apply {
            c.gridwidth = 0
            gridy = y
            gridx = 0
            insets = Insets(25, 0, 5, 0)
        })
    }


    private fun addSaveButton(y: Int = Y++) {
        add(JButton("保存配置").apply {
            // 监听
            background = Color(0x2B675F)
            addActionListener {
                val result = JOptionPane.showConfirmDialog(
                    this,
                    "是否保存配置?",
                    "误操作警告",
                    JOptionPane.YES_NO_OPTION
                )
                if (result == JOptionPane.YES_OPTION) {
                    log("保存配置")
                    SettingDataHandle(scmt).save()
                }
            }
        }, c.apply {
            c.gridwidth = 0
            gridy = y
            gridx = 0
            insets = Insets(2, 0, 5, 0)
        })
    }

    private fun addOpenConsoleButton(y: Int = Y++) {
        add(JButton("打开控制台").apply {
            // 监听
            background = Color(81, 83, 85)
            addActionListener {
                ConsoleManager.showConsole()
            }
        }, c.apply {
            c.gridwidth = 0
            gridy = y
            gridx = 0
            insets = Insets(2, 0, 5, 0)
        })
    }

    private fun addExitButton(y: Int = Y++) {
        add(JButton("退出").apply {
            // 监听
            background = Color(0x3C3C3E)
            background = Color(0xB54747)
            addActionListener {
                val result = JOptionPane.showConfirmDialog(
                    this,
                    "是否退出应用程序?",
                    "误操作警告",
                    JOptionPane.YES_NO_OPTION
                )
                if (result == JOptionPane.YES_OPTION) {
                    exitProcess(0)
                }
            }
        }, c.apply {
            c.gridwidth = 0
            gridy = y
            gridx = 0
            insets = Insets(2, 0, 28, 0)
        })
    }

    private fun addJSeparator(color: Color = Color(0x04A6E4B8), top: Int = 5, button: Int = 25, y: Int = Y++) {
        add(JSeparator().apply {
            foreground = color
        }, c.apply {
            c.gridwidth = 0
            gridy = y
            gridx = 0
            insets = Insets(top, 0, button, 0)
        })
    }

    private fun addLabel(label: String, y: Int = Y++) {
        add(JLabel(label), c.apply {
            c.gridwidth = 1
            gridy = y
            gridx = 0
            insets = Insets(5, 0, 5, 0)
        })
    }

    private fun addField(label: String, field: JTextField, y: Int = Y++) {
        c.gridwidth = 1
        c.gridx = 0
        c.gridy = y
        add(JLabel(label), c)
        c.gridx = 1
        c.insets = Insets(5, 0, 5, 0)
        add(field, c)
    }

    private fun addCheckBox(checkBox: JCheckBox, y: Int = Y++) {
        c.gridx = 0
        c.gridy = y
        c.insets = Insets(5, 0, 5, 0)
        add(checkBox, c)
    }

    private fun addFileChooserField(
        label: String,
        textField: JTextField,
        button: JButton,
        filter: FileNameExtensionFilter? = null,
        fileSelectionMode: Int = JFileChooser.FILES_ONLY,
        y: Int = Y++
    ) {
        c.gridwidth = 1
        c.gridx = 0
        c.gridy = y
        add(JLabel(label), c)
        c.gridx = 1
        add(textField, c)
        c.gridx = 2
        c.insets = Insets(5, 0, 5, 0)
        add(button, c)

        button.addActionListener {
            val fileChooser = JFileChooser()
            filter?.let { fileChooser.fileFilter = filter }
            // 设置文件选择模式，允许选择文件或文件夹
            fileChooser.fileSelectionMode = fileSelectionMode
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                textField.text = fileChooser.selectedFile.path
            }
        }
    }
}
