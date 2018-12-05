package hu.selester.seltransport.Database.Daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import hu.selester.seltransport.Database.Tables.DocsTypeTable
import hu.selester.seltransport.Database.Tables.TransportDatasTable

@Dao
interface DocsTypeDao {

    @Query("Select * from DocsTypeTable order by docName")
    fun getAll() : List<DocsTypeTable>

    @Query("Select transactId from DocsTypeTable order by transactId desc limit 1")
    fun getLastTransactID(): Int

    @Insert
    fun insert(docsTypeTable : DocsTypeTable)

    @Query("Delete from DocsTypeTable")
    fun deleteAll()

    @Query("Select count(id) from DocsTypeTable")
    fun getAllRowsNum() : Integer

}