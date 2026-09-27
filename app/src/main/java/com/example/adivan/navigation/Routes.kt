package com.example.adivan.navigation

object Routes {
    const val Splash = "splash"
    const val Home = "home"
    const val ApplicationStatus = "application_status"
    const val Scholarships = "scholarships"
    const val SchemeDetails = "scheme_details/{schemeId}"
    const val Eligibility = "eligibility/{schemeId}"
    const val Documents = "documents"
    const val DocumentDetail = "document_detail/{docId}"
    const val Funds = "funds"
    const val Profile = "profile"
    const val ProfileSection = "profile_section/{section}"
    const val Jago = "jago/{screenContext}"

    fun jago(screenContext: String) = "jago/$screenContext"
    const val ApplyConfirmation = "apply_confirmation/{schemeId}"

    fun schemeDetails(schemeId: String) = "scheme_details/$schemeId"
    fun eligibility(schemeId: String) = "eligibility/$schemeId"
    fun documentDetail(docId: String) = "document_detail/$docId"
    fun applyConfirmation(schemeId: String) = "apply_confirmation/$schemeId"
    fun profileSection(section: String) = "profile_section/$section"

    val bottomNavRoutes = setOf(Home, Scholarships, Funds, Documents, Profile)
}
