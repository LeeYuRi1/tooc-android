package com.hyeran.android.travely_manager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyeran.android.travely_manager.db.SharedPreferencesController
import com.hyeran.android.travely_manager.model.ReserveListResponseData
import com.hyeran.android.travely_manager.model.ReserveResponseDto
import com.hyeran.android.travely_manager.model.StoreResponseDto
import com.hyeran.android.travely_manager.network.NetworkService
import kotlinx.android.synthetic.main.fragment_reserve_storage_list.view.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReserveStorageListFragment : Fragment() {
    lateinit var v : View
    lateinit var reserveListRVAdapter : ReserveListRVAdapter
    lateinit var storageListRVAdapter : StorageListRVAdapter
    lateinit var networkService :NetworkService

    var r_dataList : ArrayList<ReserveResponseDto> = ArrayList()
    var s_dataList : ArrayList<StoreResponseDto> = ArrayList()

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

//        r_setRecyclerView()
//        s_setRecyclerView()
    }


//    private fun r_setRecyclerView() {
//        // 임시 데이터 1
//        r_dataList.add(ReserveListTempData(0, "박상영","결제완료", "2018.10.23", 8900, 2, "19:45"))
//    }
//
//    private fun s_setRecyclerView() {
//        // 임시 데이터 2
//        s_dataList.add(StorageListTempData(0, "박상영","결제완료", "2018.10.23", 8900, 2, "19:45"))
//        s_dataList.add(StorageListTempData(0, "박상영","결제완료", "2018.10.23", 8900, 2, "19:45"))
//    }

    private fun getReserveResponse(){
        var jwt  = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        var getReserveResponse = networkService.getReserveResponse(jwt)
        getReserveResponse.enqueue(object : Callback<ReserveListResponseData>{
            override fun onFailure(call: Call<ReserveListResponseData>, t: Throwable) {
                toast("onFailure")
            }

            override fun onResponse(call: Call<ReserveListResponseData>, response: Response<ReserveListResponseData>) {
                response?.let {
                    when(it.code()){
                        200->{
                        toast("서버성공!!")
                            r_dataList = response.body()!!.reserveResponseDtoList
                            s_dataList = response.body()!!.storeResponseDtoList
                        }
                        403->{
                            toast("인증에러")
                        }
                        500->{
                            toast("서버에러")
                        }
                        else ->toast("else 에러")
                    }
                }

            }
        })
    }
}