package com.atitienei_daniel.hampterchat.domain.use_case

import android.content.Context
import com.atitienei_daniel.hampterchat.R

class ValidateGender(private val context: Context) {

    operator fun invoke(gender: String?): ValidationResult {
        if (gender == null || gender.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = context.getString(R.string.error_unselected_gender)
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}