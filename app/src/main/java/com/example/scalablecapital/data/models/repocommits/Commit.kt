package com.example.scalablecapital.data.models.repocommits

data class Commit (
  var author: Author,
  var committer: Committer,
  var message: String,
  var tree: Tree,
  var url: String,
  var commentCount: Int,
  var verification: Verification
)