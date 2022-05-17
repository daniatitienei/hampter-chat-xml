package com.atitienei_daniel.hampterchat.domain.use_case

import android.content.Context
import com.atitienei_daniel.hampterchat.R

class ValidateUsername(private val context: Context) {

    operator fun invoke(username: String?): ValidationResult {
        if (username == null || username.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = context.getString(R.string.error_empty_username)
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}