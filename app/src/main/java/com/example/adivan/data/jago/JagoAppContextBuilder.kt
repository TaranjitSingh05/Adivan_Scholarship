package com.example.adivan.data.jago

import com.example.adivan.data.DemoData
import com.example.adivan.data.DocumentStatus

object JagoAppContextBuilder {

    fun build(screenContext: String): Map<String, Any?> {
        val student = DemoData.student
        val verification = DemoData.applicationTimeline.map { step ->
            mapOf(
                "title" to step.title,
                "state" to step.state.name,
                "subtitle" to step.subtitle,
                "date" to step.date,
            )
        }
        val documents = DemoData.documents.map { doc ->
            mapOf(
                "name" to doc.name,
                "status" to doc.status.name,
                "source" to doc.source,
                "lastVerified" to doc.lastVerified,
            )
        }
        val schemes = DemoData.schemes.map { scheme ->
            mapOf(
                "id" to scheme.id,
                "name" to scheme.name,
                "shortDescription" to scheme.shortDescription,
                "level" to scheme.level,
                "documents" to scheme.documents,
            )
        }

        return mapOf(
            "screenContext" to screenContext,
            "student" to mapOf(
                "name" to student.name,
                "category" to student.category,
                "course" to student.course,
                "institution" to student.institution,
                "studentId" to student.studentId,
            ),
            "currentApplication" to mapOf(
                "scheme" to DemoData.currentScholarshipName,
                "applicationId" to DemoData.currentApplicationNo,
                "status" to DemoData.currentStatusLabel,
                "verificationTimeline" to verification,
            ),
            "funds" to mapOf(
                "totalApproved" to DemoData.totalApprovedAmount,
                "received" to DemoData.amountReceived,
                "pending" to DemoData.pendingAmount,
                "bankAccountMasked" to DemoData.bankAccountMasked,
            ),
            "documents" to documents,
            "documentsNeedingAction" to DemoData.documents
                .filter { it.status == DocumentStatus.ActionRequired }
                .map { it.name },
            "scholarshipSchemes" to schemes,
            "dashboard" to mapOf(
                "totalApplications" to DemoData.totalApplications,
                "approvedApplications" to DemoData.approvedApplications,
                "inProgressApplications" to DemoData.inProgressApplications,
            ),
        )
    }

    fun screenContextFromRoute(route: String?): String = when (route) {
        "home" -> "home"
        "scholarships" -> "scholarships"
        "funds" -> "funds"
        "documents" -> "documents"
        "profile" -> "profile"
        "application_status" -> "application_status"
        else -> "home"
    }
}
