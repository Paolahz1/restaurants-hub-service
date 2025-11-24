package com.foodcourt.hub.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

   private Long id;
   private String name;
   private String nit;
   private String address;
   private String phoneNumber;
   private String urlLogo;
   private Long ownerId;

}
