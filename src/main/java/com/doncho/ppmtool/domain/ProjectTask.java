package com.doncho.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.validation.FieldError;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class ProjectTask
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(updatable = false, unique = true)
    private String projectSequence;

    @NotBlank(message = "Please include a project summary")
    private String summary;

    private String acceptanceCriteria;

    private String status;

    private int priority;

    @Column(updatable = false)
    private String projectIdentifier;

    private Date create_At;

    private Date update_At;


    //ManyToOne to Backlog
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "backlog_id", updatable = false, nullable = false)
    @JsonIgnore
    private Backlog backlog;


    public ProjectTask()
    {
        //Default constructor
    }

    @PrePersist
    protected void onCreate()
    {
        this.create_At = new Date();
    }

    @PreUpdate
    protected void onUodate()
    {
        this.update_At = new Date();
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getProjectSequence()
    {
        return projectSequence;
    }

    public void setProjectSequence(String projectSequence)
    {
        this.projectSequence = projectSequence;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getAcceptanceCriteria()
    {
        return acceptanceCriteria;
    }

    public void setAcceptanceCriteria(String acceptanceCriteria)
    {
        this.acceptanceCriteria = acceptanceCriteria;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    public String getProjectIdentifier()
    {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier)
    {
        this.projectIdentifier = projectIdentifier;
    }

    public Date getCreate_At()
    {
        return create_At;
    }

    public void setCreate_At(Date create_At)
    {
        this.create_At = create_At;
    }

    public Date getUpdate_At()
    {
        return update_At;
    }

    public void setUpdate_At(Date update_At)
    {
        this.update_At = update_At;
    }

    public Backlog getBacklog()
    {
        return backlog;
    }

    public void setBacklog(Backlog backlog)
    {
        this.backlog = backlog;
    }

    @Override
    public String toString()
    {
        return "ProjectTask{" +
                "id=" + id +
                ", projectSequence='" + projectSequence + '\'' +
                ", summary='" + summary + '\'' +
                ", acceptanceCriteria='" + acceptanceCriteria + '\'' +
                ", status='" + status + '\'' +
                ", priority=" + priority +
                ", projectIdentifier='" + projectIdentifier + '\'' +
                ", create_At=" + create_At +
                ", update_At=" + update_At +
                '}';
    }
}
