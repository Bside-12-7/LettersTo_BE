package com.letters.to.common.config

import org.springframework.context.annotation.Configuration
import java.util.TimeZone

@Configuration
class TimeZoneConfig {
    init {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    }
}
