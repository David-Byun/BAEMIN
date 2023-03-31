$(document).ready(function(){

    var storeAddress = $("#store_address").data("address");

    var storeName = $("#store_name").data("store_name");

    var mapContainer = document.getElementById('map'), // 지도를 표시할 div

        mapOption = {
            center: new kakao.maps.LatLng(33.25110701, 126.570667), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };

    // 지도를 생성합니다
    var map = new kakao.maps.Map(mapContainer, mapOption);

    // 주소-좌표 변환 객체를 생성합니다
    var geocoder = new kakao.maps.services.Geocoder();

    // 주소로 좌표를 검색합니다
    geocoder.addressSearch(storeAddress, function(result, status) {

        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {

            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

            // 결과값으로 받은 위치를 마커로 표시합니다
            var marker = new kakao.maps.Marker({
                map: map,
                position: coords
            });

            // 인포윈도우로 장소에 대한 설명을 표시합니다
            var infowindow = new kakao.maps.InfoWindow({
                content: '<div style="width:150px;text-align:center;padding:3px 0;">' + storeName + '</div>'
            });
            infowindow.open(map, marker);

            // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
            map.setCenter(coords);


            $(".storePosition").click(function(){
                map.panTo(coords);
            })


        }

    });


    var userAddress = $("#delivery_address").val();

    if(userAddress != "" ) {
        $(".userPosition").css("display" , "inline");

        // 주소로 좌표를 검색합니다
        geocoder.addressSearch(userAddress, function(result, status) {

            // 정상적으로 검색이 완료됐으면
            if (status === kakao.maps.services.Status.OK) {

                coords = new kakao.maps.LatLng(result[0].y, result[0].x);

                // 결과값으로 받은 위치를 마커로 표시합니다
                var marker = new kakao.maps.Marker({
                    map: map,
                    position: coords
                });

                // 인포윈도우로 장소에 대한 설명을 표시합니다
                var infowindow = new kakao.maps.InfoWindow({
                    content: '<div style="width:150px;text-align:center;padding:3px 0;">' + "배달받을위치" + '</div>'
                });
                infowindow.open(map, marker);

                $(".userPosition").click(function(){
                    map.panTo(coords);
                })
            }
        });

    }

    // css로 display none시 카카오 맵 깨짐
    $("main ul.info").hide();
    // 탭 눌렀을때 색변경 콘텐츠 변경
    $("ul.tab > li").click(function() {

        const index = $(this).index() + 1;

        $("ul.tab > li").removeClass("select");
        $(this).addClass("select");

        $("main  ul").eq(1).hide();
        $("main  ul").eq(2).hide();
        $("main  ul").eq(3).hide();
        $("main  ul").eq(index).show();

        const offset = $(".offset").offset();
        const scrollPosition = $(document).scrollTop();

        if (offset["top"] < scrollPosition) {
            $("html").animate({ scrollTop: offset.top }, 100);
        }

    });

    //메뉴 클릭시 modal 열기
    function openModal(modal) {
        const size = window.innerWidth;

        if (size > 767) {
            modal.css("transition", "0s").css("top", "0%");
            console.log("pc");
        } else {
            modal.css("transition", "0.2s").css("top", "0%");
            console.log("mobile");
        }
        $("#modal_bg").show();
        $("body").css("overflow", "hidden");
        $("body").css("overflow-y", "hidden");


        $(".closeA").click(function() {
            closeModal();
        });

        $("#modal_bg").click(function() {
            closeModal();
        });

        $(".closeB").click(function() {
            closeModal();
        });
    }



// modal 닫기
    function closeModal() {
        $("#modal_bg").hide();
        $(".modal").css("top", "100%");
        $(".modal_box").scrollTop(0);
        $("body").css("overflow", "visible");
        $(".modal input[type='checkBox']").prop("checked", false);

        $("#amount").val(1);
    };



// 메뉴 클릭시 모달창
    $(".menu > li .menu_box").click(function() {

        const foodId = $(this).find(".food_id").val();
        $.ajax({
            url: `/api/food/${foodId}/option`,
            type: "get",
        })
            .done(function(result){
                console.log(result);
                foodModalHtml(result);

                if(result.length == 0) {
                    $("#option").hide();
                } else {
                    $("#option").show();
                }
            })
            .fail(function(){
                swal("에러가 발생했습니다");
                food.hide();
            }) // ajax


        const addCartModal = $(".food_modal");
        const foodImg = $(this).find(".food_img").val();
        const foodName = $(this).find(".food_name").val();
        let foodPrice = Number($(this).find(".food_price").val());
        const foodDec = $(this).find(".food_dec").val();
        const amount = $("#amount").val();
        const totalPrice = amount * foodPrice;

        $(".menu_img").attr("src", foodImg);
        $(".menu_name").text(foodName);
        $(".menu_dec").text(foodDec);
        $(".price").text(Number(foodPrice).toLocaleString() + '원');
        $(".total_price").text(Number(totalPrice).toLocaleString() + '원');

        $(".add_cart_food_name").val(foodName);
        $(".add_cart_food_price").val(foodPrice);
        $(".add_cart_food_id").val(foodId);

        openModal(addCartModal);





        // 수량 증가 감소
        $(".minus").off().click(function() {
            if (1 < Number($("#amount").val())) {
                $("#amount").val(Number($("#amount").val()) - 1);
            }
            const amount = Number($("#amount").val());
            const totalPrice = amount * foodPrice;
            $(".total_price").text(Number(totalPrice).toLocaleString() + '원');
        })

        $(".plus").off().click(function() {
            $("#amount").val(Number($("#amount").val()) + 1);
            const amount = $("#amount").val();
            const totalPrice = amount * foodPrice;
            $(".total_price").text(Number(totalPrice).toLocaleString() + '원');
        })


        // 옵션 체크박스 변경
        $(document).off().on("click" , ".option_box i", function(){
            const optionPrice = Number($(this).siblings(".option_price").val());

            if($(this).siblings(".menu_option").is(":checked")) {
                $(this).siblings(".menu_option").prop("checked" ,false);
                $(this).css("color" , "#ddd");

                foodPrice -= optionPrice;

            } else {
                $(this).siblings(".menu_option").prop("checked" , true);
                $(this).css("color" , "#30DAD9");

                foodPrice += optionPrice;
            }

            const amount = Number($("#amount").val());
            const totalPrice = amount * foodPrice;
            $(".total_price").text(Number(totalPrice).toLocaleString() + '원');
        })

    }) // 메뉴 클릭

    //추가옵션 동적추가
    function foodModalHtml(result) {
        let html = "";

        for(var i=0;i<result.length;i++) {
            html += `<li>
            <div class="option_box">
            	<span>
        			<i class="fas fa-check-square"></i>
 	 				<input type="checkbox" class="menu_option" name="option" value="${result[i]["optionName"] }"> ${result[i]["optionName"] }
    				<input type="hidden" class="option_price" value="${result[i]["optionPrice"] }">
        			<input type="hidden" class="option_id" value="${result[i]["id"] }">
     	 		</span>
    			<span>${result[i]["optionPrice"] } 원</span>
        	</div>
      	</li>`;
        }

        $("#option ul").html(html);
    }

})