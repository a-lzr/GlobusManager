package by.a_lzr.globusmanager.deprecated

import androidx.paging.PagedList
import by.a_lzr.globusmanager.data.entity.Message

class MessagesDetailsBoundaryCallback : PagedList.BoundaryCallback<Message>() {

//    private val api = RedditService.createService()
//    private val executor = Executors.newSingleThreadExecutor()
//    private val helper = PagingRequestHelper(executor)

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
//1
/*        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) { helperCallback ->
            api.getPosts()
                //2
                .enqueue(object : Callback<RedditApiResponse> {

                    override fun onFailure(call: Call<RedditApiResponse>?, t: Throwable) {
                        //3
                        Log.e("RedditBoundaryCallback", "Failed to load data!")
                        helperCallback.recordFailure(t)
                    }

                    override fun onResponse(
                        call: Call<RedditApiResponse>?,
                        response: Response<RedditApiResponse>) {
                        //4
                        val posts = response.body()?.data?.children?.map { it.data }
                        executor.execute {
                            db.postDao().insert(posts ?: listOf())
                            helperCallback.recordSuccess()
                        }
                    }
                })
        } */
    }

    override fun onItemAtEndLoaded(itemAtEnd: Message) {
        super.onItemAtEndLoaded(itemAtEnd)
/*        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) { helperCallback ->
            api.getPosts(after = itemAtEnd.key)
                .enqueue(object : Callback<RedditApiResponse> {

                    override fun onFailure(call: Call<RedditApiResponse>?, t: Throwable) {
                        Log.e("RedditBoundaryCallback", "Failed to load data!")
                        helperCallback.recordFailure(t)
                    }

                    override fun onResponse(
                        call: Call<RedditApiResponse>?,
                        response: Response<RedditApiResponse>) {

                        val posts = response.body()?.data?.children?.map { it.data }
                        executor.execute {
                            db.postDao().insert(posts ?: listOf())
                            helperCallback.recordSuccess()
                        }
                    }
                })
        }*/
    }
}