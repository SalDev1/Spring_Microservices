package com.socialmediaapi.demo.filtering;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {

    // Static Filtering Example
    @GetMapping("/filtering/static")
    public SomeBean filteringStatic() {
        // MappingJacksonValue --> Dynamic Filtering
        SomeBean someBean =  new SomeBean("value1" , "value2" , "value3");
        return someBean;
    }

    // Dynamic Filtering Example
    @GetMapping("/filtering/dynamic")
    public MappingJacksonValue filteringDynamic() {
        // MappingJacksonValue --> Dynamic Filtering
        SomeBean someBean =  new SomeBean("value1" , "value2" , "value3");

        // This gives us additional capability to add filters.
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);

        // Creating our own filter and pushing into it.
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1" , "field3");

        // Allowing Field1 and Field3 only in the filter.
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter" , filter);
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }

    // Static Filtering Example
    @GetMapping("/filtering-list/static")
    public List<SomeBean> filteringListStatic() {
        return Arrays.asList(new SomeBean("value1" , "value2" , "value3") ,
                new SomeBean("value4" , "value5" , "value6"));
    }

    // Dynamic Filtering Example
    @GetMapping("/filtering-list/dynamic")
    public MappingJacksonValue filteringListDynamic() {
        List<SomeBean> list = Arrays.asList(new SomeBean("value1" , "value2" , "value3") ,
                new SomeBean("value4" , "value5" , "value6"));

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);

        // Creating our own filter and pushing into it.
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2" , "field3");

        // Allowing Field1 and Field3 only in the filter.
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter" , filter);
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }
}
