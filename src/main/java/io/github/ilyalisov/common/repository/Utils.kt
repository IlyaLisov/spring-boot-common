package io.github.ilyalisov.common.repository

/**
 * Converts a search query string to PostgreSQL tsquery format by adding
 * prefix matching operators to each word. This enables partial word
 * matching in full-text search queries.
 *
 * @param query the original search query string, can be null
 * @return the query formatted for PostgreSQL tsquery with prefix matching,
 *         or null if input query is null or contains only whitespace
 */
fun modifyFTS(
    query: String?
): String? {
    if (query.isNullOrBlank()) {
        return null
    }
    return query.split("\\s+".toRegex())
        .filter { it.isNotBlank() }
        .joinToString(" & ") { "$it:*" }
        .takeIf { it.isNotBlank() }
}