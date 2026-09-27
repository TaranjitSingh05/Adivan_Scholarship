package com.example.adivan.data

data class Student(
    val name: String,
    val category: String,
    val course: String,
    val institution: String,
    val studentId: String,
)

data class ScholarshipScheme(
    val id: String,
    val name: String,
    val shortDescription: String,
    val about: String,
    val level: String,
    val beneficiaries: String,
    val financialAssistance: String,
    val documents: List<String>,
)

data class ApplicationProgressStep(
    val label: String,
    val state: StepState,
)

enum class StepState { Completed, Current, Pending }

data class TimelineItem(
    val title: String,
    val date: String?,
    val subtitle: String,
    val state: StepState,
)

data class StudentDocument(
    val id: String,
    val name: String,
    val status: DocumentStatus,
    val source: String?,
    val lastVerified: String?,
    val maskedNumber: String,
)

enum class DocumentStatus { Verified, ActionRequired, Pending }

data class FundTransaction(
    val title: String,
    val date: String,
    val amount: String,
    val status: String,
)

object DemoData {
    val student = Student(
        name = "Rahul Kumar",
        category = "ST",
        course = "B.Tech (CSE)",
        institution = "JIS University",
        studentId = "ST20260001",
    )

    const val currentScholarshipName = "Post-Matric Scholarship"
    const val currentApplicationNo = "PMS2026001234"
    const val currentStatusLabel = "Under Verification"

    val homeProgressSteps = listOf(
        ApplicationProgressStep("Submitted", StepState.Completed),
        ApplicationProgressStep("Documents Verified", StepState.Completed),
        ApplicationProgressStep("Institution Pending", StepState.Current),
        ApplicationProgressStep("Department", StepState.Pending),
        ApplicationProgressStep("Approved", StepState.Pending),
    )

    val applicationTimeline = listOf(
        TimelineItem(
            title = "Application Submitted",
            date = "12 Aug 2026",
            subtitle = "Application received successfully",
            state = StepState.Completed,
        ),
        TimelineItem(
            title = "Document Verification",
            date = "18 Aug 2026",
            subtitle = "Documents verified",
            state = StepState.Completed,
        ),
        TimelineItem(
            title = "Institution Verification",
            date = null,
            subtitle = "Your application is currently under institution verification.",
            state = StepState.Current,
        ),
        TimelineItem(
            title = "Department Verification",
            date = null,
            subtitle = "Pending",
            state = StepState.Pending,
        ),
        TimelineItem(
            title = "Approved",
            date = null,
            subtitle = "Pending",
            state = StepState.Pending,
        ),
        TimelineItem(
            title = "Sanctioned",
            date = null,
            subtitle = "Pending",
            state = StepState.Pending,
        ),
        TimelineItem(
            title = "DBT Payment",
            date = null,
            subtitle = "Pending",
            state = StepState.Pending,
        ),
    )

    const val totalApplications = 2
    const val approvedApplications = 1
    const val inProgressApplications = 1
    /** Dashboard quick stat (reference mockup); full disbursement totals are on the Funds screen. */
    const val homeFundsReceivedDisplay = "₹12,500"

    const val totalApprovedAmount = "₹1,20,000"
    const val amountReceived = "₹80,000"
    const val pendingAmount = "₹40,000"
    const val schemeReceived = "₹80,000"
    const val schemeTotal = "₹1,20,000"
    const val bankAccountMasked = "**** 4507"

    val transactions = listOf(
        FundTransaction("DBT Credit", "12 Sep 2026", "+ ₹20,000", "Completed"),
        FundTransaction("DBT Credit", "05 Sep 2026", "+ ₹20,000", "Completed"),
        FundTransaction("DBT Credit", "12 Mar 2026", "+ ₹40,000", "Completed"),
    )

    val schemes = listOf(
        ScholarshipScheme(
            id = "pre_matric",
            name = "Pre-Matric Scholarship",
            shortDescription = "For ST students (Class 1–10)",
            about = "Financial assistance to eligible ST students studying in classes 1 to 10 in recognized schools.",
            level = "Class 1 to 10",
            beneficiaries = "ST students",
            financialAssistance = "Tuition fee, books, and maintenance allowance as per norms",
            documents = listOf(
                "ST Certificate",
                "Income Certificate",
                "School admission proof",
                "Previous year marksheet",
                "Bank details",
            ),
        ),
        ScholarshipScheme(
            id = "post_matric",
            name = "Post-Matric Scholarship",
            shortDescription = "For ST students (Class 11 and above)",
            about = "Financial assistance to eligible ST students pursuing post-matriculation studies in recognized institutions.",
            level = "Class 11 and above",
            beneficiaries = "ST students",
            financialAssistance = "Tuition fee, maintenance allowance and other applicable support",
            documents = listOf(
                "ST Certificate",
                "Income Certificate",
                "Academic records",
                "Institution details",
                "Bank details",
            ),
        ),
        ScholarshipScheme(
            id = "top_class",
            name = "Top Class Scholarship",
            shortDescription = "For professional & technical courses",
            about = "Support for ST students pursuing professional and technical degree/diploma courses in top institutions.",
            level = "Professional & technical courses",
            beneficiaries = "ST students",
            financialAssistance = "Full tuition, living allowance, and other prescribed benefits",
            documents = listOf(
                "ST Certificate",
                "Income Certificate",
                "Admission letter",
                "Course fee structure",
                "Bank details",
            ),
        ),
        ScholarshipScheme(
            id = "nfst",
            name = "National Fellowship (NFST)",
            shortDescription = "For ST students (M.Phil / PhD)",
            about = "Fellowship for ST students pursuing M.Phil and PhD in universities and research institutions.",
            level = "M.Phil / PhD",
            beneficiaries = "ST students",
            financialAssistance = "Fellowship amount, contingency, and research support",
            documents = listOf(
                "ST Certificate",
                "Research proposal",
                "Supervisor acceptance",
                "Academic records",
                "Bank details",
            ),
        ),
        ScholarshipScheme(
            id = "nos",
            name = "National Overseas Scholarship (NOS)",
            shortDescription = "For higher studies abroad",
            about = "Scholarship for ST students pursuing master's and PhD programs at reputed foreign universities.",
            level = "Higher studies abroad",
            beneficiaries = "ST students",
            financialAssistance = "Tuition, living expenses, travel, and insurance as per scheme guidelines",
            documents = listOf(
                "ST Certificate",
                "Offer letter from university",
                "Passport",
                "Academic records",
                "Bank details",
            ),
        ),
    )

    val documents = listOf(
        StudentDocument("aadhaar", "Aadhaar Card", DocumentStatus.Verified, "DigiLocker", "10 Aug 2026", "XXXX-XXXX-4521"),
        StudentDocument("st_cert", "ST Certificate", DocumentStatus.Verified, "e-District", "08 Aug 2026", "ST/WB/2024/88421"),
        StudentDocument("income", "Income Certificate", DocumentStatus.ActionRequired, null, null, "INC/2026/Pending"),
        StudentDocument("domicile", "Domicile Certificate", DocumentStatus.Verified, null, "05 Aug 2026", "DOM/WB/112233"),
        StudentDocument("marksheet", "Marksheet", DocumentStatus.Verified, "APAAR", "09 Aug 2026", "APAAR-ID-77821"),
        StudentDocument("college", "College Certificate", DocumentStatus.Verified, "AISHE", "07 Aug 2026", "AISHE-C-99201"),
        StudentDocument("bank", "Bank Account Proof", DocumentStatus.Verified, null, "06 Aug 2026", "A/C ****4507"),
    )

    fun schemeById(id: String): ScholarshipScheme? = schemes.find { it.id == id }

    fun documentById(id: String): StudentDocument? = documents.find { it.id == id }
}
