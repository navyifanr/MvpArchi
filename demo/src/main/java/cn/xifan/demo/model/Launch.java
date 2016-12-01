package cn.xifan.demo.model;

/**
 * @author xifan
 *         date: 2016/8/29
 *         desc:
 */
public class Launch extends BaseModel{
    private String text;
    private String img;

    public String getText() {
        return text==null ? "" : text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
