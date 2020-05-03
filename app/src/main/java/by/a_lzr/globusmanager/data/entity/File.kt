package by.a_lzr.globusmanager.data.entity

import androidx.room.ColumnInfo

data class File (
    val name: String,
    val extension: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var body: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as File

        if (name != other.name) return false
        if (extension != other.extension) return false
        if (!body.contentEquals(other.body)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + extension.hashCode()
        result = 31 * result + body.contentHashCode()
        return result
    }
}