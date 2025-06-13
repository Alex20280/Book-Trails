package com.psfilter.feature_auth_module.ui

object AuthFields {
    @JvmInline
    value class Email(val raw: String)

    @JvmInline
    value class Password(val raw: String)

    @JvmInline
    value class ConfirmPassword(val raw: String)

    @JvmInline
    value class Name(val raw: String)

    @JvmInline
    value class VerificationCode(val raw: String)
}