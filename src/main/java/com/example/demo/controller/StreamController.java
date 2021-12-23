package com.example.demo.controller;

import com.example.demo.dto.StreamDto;
import com.example.demo.entity.Stream;
import com.example.demo.repository.StreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stream/")
public class StreamController {

    @Autowired
    StreamRepository streamRepository;

    @GetMapping("all")
    public Iterable<Stream> getStreams(){
        return streamRepository.findAll();
    }

    @GetMapping("testNativeList")
    public List<Object[]> getNativeList() {
        return streamRepository.getResults();
    }

    @GetMapping("findFirst")
    public StreamDto findFirst() {
       Stream stream = streamRepository.findLast();
       return new StreamDto(stream);
    }

    @GetMapping("getCertainColumns")
    public List<Object[]> getCertainColumns() {

        return streamRepository.getNamesAndCodes();
    }

    @GetMapping("testNativeMap")
    public Map<String, BigInteger> getNativeMap() {
        /*List<BigInteger> list = new ArrayList<>();
        Map<String, Object> map = streamRepository.getMappedResults();
        map.forEach((k,v)-> {
            list.add((BigInteger) v);
        });
        System.out.println("List: "+ Arrays.toString(list.toArray()));*/
        return streamRepository.getMappedResults();
    }

}
