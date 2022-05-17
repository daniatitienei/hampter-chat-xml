package com.atitienei_daniel.hampterchat.domain.use_case

import android.content.Context
import android.util.Patterns
import com.atitienei_daniel.hampterchat.R

class ValidateEmail(private val context: Context) {

    operator fun invoke(email: String?): ValidationResult {
        if (email == null || !Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = context.getString(R.string.error_invalid_email)
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}