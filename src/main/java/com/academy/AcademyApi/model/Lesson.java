package com.academy.AcademyApi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "Lessons", schema = "ACADEMY")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Lesson implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long lessonId;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "career_id", nullable = false)
    private Career career;

    @Column(name = "num_bimester", nullable = false)
    private Integer numBimester;

    @Min(1)
    @Max(8)
    @Column(name = "num_week", nullable = false)
    private Integer numWeek;

    @Column(name = "pos_week", nullable = false)
    private Integer posWeek;

    @Column(name = "lesson_title", length = 180, nullable = false)
    private String lessonTitle;

    @Column(name = "teacher_name", length = 60, nullable = false)
    private String teacherName;

    @Column(name = "content_descrip", length = 255)
    private String contentDescrip;

    @Column(name = "link_to_content", length = 255)
    private String linkToContent;

    @Column(name = "content_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ContentTypeEnum contentType;

    public enum ContentTypeEnum {
        PDF, VIDEO
    }
}
