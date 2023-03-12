package com.example.scalablecapital.data.models.repocommits

data class CommitResponse (
  var sha: String,
  var nodeId: String,
  var commit: Commit
)