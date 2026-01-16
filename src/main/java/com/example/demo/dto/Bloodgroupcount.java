package com.example.demo.dto;

import com.example.demo.Entity.type.Bloodgrouptype;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Bloodgroupcount {
    private Bloodgrouptype bloodgrouptype;
    private Long count;
}
