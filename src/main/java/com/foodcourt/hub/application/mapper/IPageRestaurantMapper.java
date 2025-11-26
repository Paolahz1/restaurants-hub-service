package com.foodcourt.hub.application.mapper;

 import com.foodcourt.hub.application.dto.GetPageRestaurantsResponse;
 import com.foodcourt.hub.application.dto.RestaurantSummaryResponse;
 import com.foodcourt.hub.domain.model.Restaurant;
import org.mapstruct.Mapper;
 import org.springframework.data.domain.Page;

 import java.util.List;


@Mapper(componentModel  = "spring")
public interface IPageRestaurantMapper {

    List<RestaurantSummaryResponse> toSummaryList(List<Restaurant> restaurants);

    default GetPageRestaurantsResponse toResponse(Page<Restaurant> page) {
        GetPageRestaurantsResponse response = new GetPageRestaurantsResponse();

        response.setContent(toSummaryList(page.getContent()));
        response.setPage(page.getNumber());
        response.setSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setFirst(page.isFirst());
        response.setLast(page.isLast());

        return response;
    }

}
