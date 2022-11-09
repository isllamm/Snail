package com.tawajood.snail

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.myfatoorah.sdk.utils.MFCountry
import com.myfatoorah.sdk.utils.MFEnvironment
import com.myfatoorah.sdk.views.MFSDK
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.updateLanguage
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp
class MyApp: Application() {

    val token = "EQGCV7fyuVwcBLRfgxOtKMl_8IOgsLVvhD__WiPaOn9leBYXFXew2nch9gDxvjApLeiKAhaj2mkkcM3Cm2fsdnFDr-FGG_U5oplEpcdn1uea8gh7j8PVlepxL5EDAYzNusRAeizrIGi0-X5SIOxNzADpj6Degg7S07Bhi1f6zSXwLJyr340hfhN4CuKSb3-NpY8_DBTMJquWpyyuha7O58SZN441kjNZxZXTehkW2CfeVpIBPjbkcUFhAEIVBooJjAppJCAyrkez5fOaCLmABeHfnKVMmnRLTosI_oOLcp1ssSg_JRwfdEkcahiC24Dyl2iOjC_7Tij_uGxIvKT8sR1zINloJcNvgvs8ELgDxdwQbN2SGv4iKhIMzt5Rj9iqaqcnV0n3b8rmD-3WiQCTHAvcxG4CCIXXwZyCvLI6Jkcq0MOwEQf7_weCLhA_M1ZTg01l3byBEc059s3Gtu2Wz42GuhAI23ZX1nl1OlFEb4fD029q4fVDHGsSMFQQGtmtcx4_VLGAYb3EXbRQYUOyuHW3DkDpx0AYT0H7en4SEiB_fSlp3ZXH5WarkoXnGTUJ_1IHsPqtHUZeby2tCgb5-5ycZY34bkiaTu_2IcnKXdaCTubb7Rj4sy-atslcvH_kTZq9TRhlkSDsK-vtoCMqvAfmtGUFk0qxM0p9nZnYvZ6s2UXL"
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        MFSDK.init(token, MFCountry.SAUDI_ARABIA, MFEnvironment.LIVE)

        PrefsHelper.init(applicationContext)
        Lingver.init(this, Locale("ar", "EG"))
        updateLanguage(applicationContext)
    }
}