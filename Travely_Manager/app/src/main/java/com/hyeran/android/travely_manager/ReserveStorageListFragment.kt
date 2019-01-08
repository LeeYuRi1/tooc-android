package com.hyeran.android.travely_manager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyeran.android.travely_manager.db.SharedPreferencesController
import com.hyeran.android.travely_manager.model.ReserveListResponseData
import com.hyeran.android.travely_manager.network.NetworkService
import kotlinx.android.synthetic.main.fragment_reserve_storage_list.view.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReserveStorageListFragment : Fragment() {
    lateinit var v : View
    lateinit var reserveListRVAdapter : ReserveListRVAdapter
    lateinit var storageListRVAdapter: StorageListRVAdapter
    lateinit var networkService :NetworkService

    public var r_dataList : ArrayList<ReserveListTempData> = ArrayList()
    public var s_dataList : ArrayList<StorageListTempData> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v =  inflater.inflate(R.layout.fragment_reserve_storage_list, container, false)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var reserveListRVAdapter = ReserveListRVAdapter(context, r_dataList)
        v.rv_reserve_list.adapter = reserveListRVAdapter
        v.rv_reserve_list.layoutManager = LinearLayoutManager(context)

        var storageListRVAdapter = StorageListRVAdapter(context, s_dataList)
        v.rv_storage_list.adapter = storageListRVAdapter
        v.rv_storage_list.layoutManager = LinearLayoutManager(context)

        r_setRecyclerView()
        s_setRecyclerView()

//        var dataList : ArrayList<StorageListTempData> = ArrayList()
//        dataList.add(StorageListTempData(0, "박상영", 8900, 2, "오후", 7, 40))
//        dataList.add(StorageListTempData(0, "최유성", 8900, 2, "오후", 7, 40))
//        dataList.add(StorageListTempData(0, "최정연", 8900, 2, "오후", 7, 40))
//
//        storageListRVAdapter = StorageListRVAdapter(context, dataList)
//        v.rv_storage_list_storage_list.adapter = storageListRVAdapter
//        v.rv_storage_list_storage_list.layoutManager = LinearLayoutManager(context)
    }


    private fun r_setRecyclerView() {
        // 임시 데이터 1
        r_dataList.add(ReserveListTempData(0, "박상영","결제완료", "2018.10.23", 8900, 2, "19:45"))
    }

    private fun s_setRecyclerView() {
        // 임시 데이터 2
        s_dataList.add(StorageListTempData(0, "박상영","결제완료", "2018.10.23", 8900, 2, "19:45"))
        s_dataList.add(StorageListTempData(0, "박상영","결제완료", "2018.10.23", 8900, 2, "19:45"))
    }

    private fun getReserveResponse(){
        var jwt  = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        var getReserveResponse = networkService.getReserveResponse(jwt)
        getReserveResponse.enqueue(object : Callback<ReserveListResponseData>{
            override fun onFailure(call: Call<ReserveListResponseData>, t: Throwable) {4
                toast("onFailure")
            }

            override fun onResponse(call: Call<ReserveListResponseData>, response: Response<ReserveListResponseData>) {
                response?.let {
                    when(it.code()){
                        200->{

                        }
                        403->{
                            toast("인증에러")
                        }
                        500->{
                            toast("서버에러")
                        }
                    }
                }

            }
        })
    }
}