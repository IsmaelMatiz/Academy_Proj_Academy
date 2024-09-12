package com.academy.AcademyApi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentProgressInfo {
    StudentsTeachers studentInfo;
    ProgressTracker studentProgress;
}
