package com.example.demo.dto;

import com.example.demo.entity.Stream;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StreamDto {

    private long streamId;

    private String name;

    private String code;

    private List<SubjectDto> subjects;

    public StreamDto(Stream stream) {
        if(stream != null && stream.getStreamId() != 0) {
            this.streamId = stream.getStreamId();
            this.name = stream.getName();
            this.code = stream.getCode();
        }
    }

}
