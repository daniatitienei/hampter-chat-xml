package com.atitienei_daniel.hampterchat.domain.use_case

import android.content.Context
import com.atitienei_daniel.hampterchat.R

class ValidateName(private val context: Context) {
    operator fun invoke(name: String?): ValidationResult {
        if (name == null || name.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = context.getString(R.string.error_empty_name)
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}