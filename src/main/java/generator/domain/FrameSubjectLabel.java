package generator.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 题目标签表
* @TableName frame_subject_label
*/
public class FrameSubjectLabel implements Serializable {

    /**
    * 主键
    */
    @NotNull(message="[主键]不能为空")
    @ApiModelProperty("主键")
    private Long id;
    /**
    * 标签分类
    */
    @Size(max= 32,message="编码长度不能超过32")
    @ApiModelProperty("标签分类")
    @Length(max= 32,message="编码长度不能超过32")
    private String labelName;
    /**
    * 排序
    */
    @ApiModelProperty("排序")
    private Integer sortNum;
    /**
    * 
    */
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("")
    @Length(max= 50,message="编码长度不能超过50")
    private String categoryId;
    /**
    * 创建人
    */
    @Size(max= 32,message="编码长度不能超过32")
    @ApiModelProperty("创建人")
    @Length(max= 32,message="编码长度不能超过32")
    private String createdBy;
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private Date createdTime;
    /**
    * 更新人
    */
    @Size(max= 32,message="编码长度不能超过32")
    @ApiModelProperty("更新人")
    @Length(max= 32,message="编码长度不能超过32")
    private String updateBy;
    /**
    * 更新时间
    */
    @ApiModelProperty("更新时间")
    private Date updateTime;
    /**
    * 
    */
    @ApiModelProperty("")
    private Integer isDeleted;

    /**
    * 主键
    */
    private void setId(Long id){
    this.id = id;
    }

    /**
    * 标签分类
    */
    private void setLabelName(String labelName){
    this.labelName = labelName;
    }

    /**
    * 排序
    */
    private void setSortNum(Integer sortNum){
    this.sortNum = sortNum;
    }

    /**
    * 
    */
    private void setCategoryId(String categoryId){
    this.categoryId = categoryId;
    }

    /**
    * 创建人
    */
    private void setCreatedBy(String createdBy){
    this.createdBy = createdBy;
    }

    /**
    * 创建时间
    */
    private void setCreatedTime(Date createdTime){
    this.createdTime = createdTime;
    }

    /**
    * 更新人
    */
    private void setUpdateBy(String updateBy){
    this.updateBy = updateBy;
    }

    /**
    * 更新时间
    */
    private void setUpdateTime(Date updateTime){
    this.updateTime = updateTime;
    }

    /**
    * 
    */
    private void setIsDeleted(Integer isDeleted){
    this.isDeleted = isDeleted;
    }


    /**
    * 主键
    */
    private Long getId(){
    return this.id;
    }

    /**
    * 标签分类
    */
    private String getLabelName(){
    return this.labelName;
    }

    /**
    * 排序
    */
    private Integer getSortNum(){
    return this.sortNum;
    }

    /**
    * 
    */
    private String getCategoryId(){
    return this.categoryId;
    }

    /**
    * 创建人
    */
    private String getCreatedBy(){
    return this.createdBy;
    }

    /**
    * 创建时间
    */
    private Date getCreatedTime(){
    return this.createdTime;
    }

    /**
    * 更新人
    */
    private String getUpdateBy(){
    return this.updateBy;
    }

    /**
    * 更新时间
    */
    private Date getUpdateTime(){
    return this.updateTime;
    }

    /**
    * 
    */
    private Integer getIsDeleted(){
    return this.isDeleted;
    }

}
