package com.arifinfrds.papbl_sqlite.ui.barang

import com.arifinfrds.papbl_sqlite.model.Barang

/**
 * Created by arifinfrds on 2/22/18.
 */
interface BarangContract {

    interface View {

        fun showToastMessage(message: String)

        fun showDialog(title: String, message: String)

        fun showIDBarangError()

        fun showNamaBarangError()

        fun showBrandBarangError()

        fun emptyInput()

    }

    interface Presenter {

        fun attemptShowToasMessage(message: String)

        fun attemptShowDialog(title: String, message: String)

        fun attemptInsert(barang: Barang)

        fun attemptFetchAll()

        fun attemptFetch(namaBarang: String)

        fun attemptUpdate(barang: Barang)

        fun attemptDelete(idBarang: Int)

        fun attemptInsertTransaction()

        fun attemptDeleteAll()


        interface OnInsertFinishListener {

            fun onInsertSuccess()

            fun onInsertFailure()

        }

        interface OnFetchAllFinishListener {

            fun onFetchAllSuccess(stringBuffer: StringBuffer)

            fun onFetchAllFailure()

        }

        interface OnFetchFinishListener {

            fun onFetchFinishSuccess(stringBuffer: StringBuffer)

            fun onFetchFinishFailure()

        }

        interface OnUpdateFinishListener {

            fun onUpdateSuccess()

            fun onUpdateFailure()

        }

        interface OnDeleteFinishListener {

            fun onDeleteSuccess()

            fun onDeleteFailure()

        }

        interface OnInsertTransactionListener {

            fun onInsertTransactionSuccess()

            fun onInsertTransactionFailure()

        }

        interface OnDeleteAllFinishListener {

            fun onDeleteAllSuccess()

            fun onDeleteAllFailure()

        }


    }

    interface Interactor {

        /**
         * method is used for checking valid input empty or not.
         *
         * @param text
         * @return boolean true for valid false for invalid
         */

        fun isInputEmpty(text: String): Boolean

        fun insert(barang: Barang, listener: Presenter.OnInsertFinishListener)

        fun fetchAll(listener: Presenter.OnFetchAllFinishListener)

        fun fetch(namaBarang: String, listener: Presenter.OnFetchFinishListener)

        fun update(barang: Barang, listener: Presenter.OnUpdateFinishListener)

        fun delete(idBarang: Int, listener: Presenter.OnDeleteFinishListener)

        fun insertBarangTransaction(listOnInsertTransactionListener: Presenter.OnInsertTransactionListener)

        fun deleteAll(listener: Presenter.OnDeleteAllFinishListener)
    }


}