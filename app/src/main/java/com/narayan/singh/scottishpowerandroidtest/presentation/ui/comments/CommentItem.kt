package com.narayan.singh.scottishpowerandroidtest.presentation.ui.comments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.narayan.singh.scottishpowerandroidtest.R
import com.narayan.singh.scottishpowerandroidtest.common.theme.Dimens
import com.narayan.singh.scottishpowerandroidtest.common.theme.ScottishPowerAndroidTestTheme
import com.narayan.singh.scottishpowerandroidtest.domain.model.Comment

@Composable
fun CommentItem(
    comment: Comment,
    showDetails: Boolean = false,
    disableClick: Boolean = false,
    onClick: () -> Unit = {}
) {
    val cardModifier = if (disableClick) {
        Modifier
    } else {
        Modifier.clickable { onClick() }
    }
    Card(
        modifier = cardModifier
            .fillMaxWidth()
            .padding(Dimens.SmallPadding),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.CardElevation),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(Dimens.MediumPadding)) {

            Text(text = comment.name, style = MaterialTheme.typography.titleLarge)
            Text(text = comment.body, style = MaterialTheme.typography.bodyMedium)

            if (showDetails) {
                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = Dimens.SmallPadding)
                        .testTag("horizontalDivider"),
                    thickness = Dimens.DividerThickness
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.comment_id_label),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.testTag("commentIdLabel")
                    )
                    Text(text = comment.id.toString(), style = MaterialTheme.typography.bodyMedium)
                }

                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = Dimens.SmallPadding)
                        .testTag("horizontalDivider"),
                    thickness = Dimens.DividerThickness
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.comment_email_label),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.testTag("commentEmailLabel")
                    )
                    Text(text = comment.email, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCommentItem() {
    ScottishPowerAndroidTestTheme {
        CommentItem(
            comment = Comment(1, 1, "User1", "user1@example.com", "Test comment 1"),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCommentItemDetails() {
    ScottishPowerAndroidTestTheme {
        CommentItem(
            comment = Comment(1, 1, "User1", "user1@example.com", "Test comment 1"),
            showDetails = true // For details screen preview
        )
    }
}