package ru.otus.dz_2024_01

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * Получить данные экземпляра, не относящиеся к конфигурации,
     * которые ранее были возвращены функцией onRetainNonConfigurationInstance().
     */
//    override fun getLastNonConfigurationInstance(): Any? {
//        return super.getLastNonConfigurationInstance()
//    }

    /**
     * Вызывается системой в рамках уничтожения activity из-за
     * изменения конфигурации, когда известно, что для новой
     * конфигурации будет немедленно создан новый экземпляр.
     * Здесь вы можете вернуть любой объект, который вам нравится,
     * включая сам экземпляр activity, который впоследствии можно получить,
     * вызвав getLastNonConfigurationInstance() в новом экземпляре activity.
     */
//    override fun onRetainNonConfigurationInstance(): Any? {
//        return super.onRetainNonConfigurationInstance()
//    }
}