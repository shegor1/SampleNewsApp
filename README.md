Sample News App - это приложение - новостной агрегатор.

Приложение написано на Kotlin. Архитектура - MVVM.

Используемые библиотеки и компоненты: room, datastorePrefs, livedata, coroutines, navComponents, safeArgs materialDesign, retrofit, moshi, glide, viewPager2, leakcanary

Приложение состоит из 5 экранов:   
-новостная лента;  
-поиск новостей по ключевому слову;  
-закладки, куда можно сохранить новость;  
-настройки, где можно сменить локалицацию новостей, выдоваемых в новостной ленте.  

В новостной ленте есть фильтрация по категориям (наука, развлечения...), реализованная с помощю viewPager2.

![image](https://user-images.githubusercontent.com/72992696/151513683-0e83f35b-3ac9-4952-b0e9-0effddfd6328.png) ![image](https://user-images.githubusercontent.com/72992696/151513763-b4560eb3-4299-438b-a946-e90c5b1cd52f.png) 
![image](https://user-images.githubusercontent.com/72992696/151513828-00d71e11-710a-4b5d-8d11-a13c5e53682f.png) ![image](https://user-images.githubusercontent.com/72992696/151513886-ea5c4e13-e5bf-4b46-b1c0-4a5154c4a38b.png)

Переход на данные экраны осуществляется с помощю bottom navigation view.  
Пятый экран - это детализация новости. На нее можно перейти из новостной ленты, поиска и закладок.

![image](https://user-images.githubusercontent.com/72992696/151514076-c7517fb7-44b7-4238-acf3-8eef50d6154e.png)

Приложение поддерживает русский и английский язык(смена происходит при смене системного языка устройства).

![image](https://user-images.githubusercontent.com/72992696/151514513-7cb21889-2b9d-459d-ad75-ef72af8cb68c.png) ![image](https://user-images.githubusercontent.com/72992696/151514529-ee3c12e9-35f7-49a2-bb3b-61987763a2ec.png)

При отсутствии результата или подключения к сети выводятся соответствующие оповещения.

![image](https://user-images.githubusercontent.com/72992696/151514850-6eda2e7e-712f-4f93-aa3f-de9ef2d73474.png) ![image](https://user-images.githubusercontent.com/72992696/151514883-179e5b77-a1d1-479d-bb14-082faf88c56f.png)

