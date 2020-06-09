# FinalProjectMCS (AturinAja)

Team : 
- Luis Anthonie Alkins (2201766601)
- Christopher Teddy Mienarto (2201765265)
- Arvin Indrawan (2201727374)
- Jennifer Goldwin (2201750761)
- Garry Stevens (2201825926)

AturinAja merupakan aplikasi travel planner yang berfungsi untuk menglist destinasi" yang ingin kita tuju pada hari tertentu. Semua data 
disimpan ke server sehingga file tersebut aman meskipun kita mendelete aplikasi/clear data. Setiap plan yang dibuat dapat kita share ke 
orang lain sehingga orang lain juga dapat melihat plan tersebut dan juga mengeditnya. 

Back End yang dipakai menggunakan nodejs+express dengan database mongoDB yg disimpan ke dalam firebase. Terdapat pula dokumentasi untuk backend tersebut di link https://github.com/christoted/AturinAja-backend.git.

Fitur utama : 
- Fitur Simulasi perjalanan
- Fitur Optimisasi perjalanan
- Fitur Sharing Plan ke user lain
- Fitur Searching, Filtering, dan Sorting pada Menu Explore

Algoritma yang dipakai : 
1. TSP, untuk mencari rute teroptimal. (TSP.java)
2. Binary Search untuk mempercepat proses pencarian destinasi tertentu dari sekian bnyk destinasi yang ada. (Handle.java)
3. Merge Sort(Collection.sort) untuk mengurutkan data dalam fitur sorting. (ExploreAdapter.java)
4. Hashing link untuk mengenkripsi id planning ketika kita melakukan sharing (PlanActivity.java)
