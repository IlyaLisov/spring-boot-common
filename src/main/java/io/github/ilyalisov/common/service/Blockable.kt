package io.github.ilyalisov.common.service

import java.util.*

interface Blockable {

    fun block(
        id: UUID
    )

    fun unblock(
        id: UUID
    )

}