package br.edu.ifsp.scl.moviesmanager.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity
data class Movie(
    @PrimaryKey
    var title: String,
    var releaseYears: String,
    var production: String,
    var duration: Long,
    var watched: Int = INT_BOOL_FALSE,
    var score: Int = INT_INVALID_SCORE,
    var genre: String,
    var url: String,
): Parcelable {
    companion object {
        const val INT_INVALID_SCORE = -1
        const val INT_BOOL_TRUE = 1
        const val INT_BOOL_FALSE = 0
    }
}
