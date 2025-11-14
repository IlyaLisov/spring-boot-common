package io.github.ilyalisov.common.service

import java.util.*

interface Moderatable : Blockable {

    fun allow(
        id: UUID
    )

    fun disallow(
        id: UUID
    )

}