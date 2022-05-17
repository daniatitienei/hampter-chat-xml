package com.atitienei_daniel.hampterchat.domain.use_case

import android.content.Context
import android.util.Patterns
import com.atitienei_daniel.hampterchat.R

class ValidatePassword(private val context: Context) {

    operator fun invoke(password: String?): ValidationResult {
        if (password == null || password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = context.getString(R.string.error_too_short_password)
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}