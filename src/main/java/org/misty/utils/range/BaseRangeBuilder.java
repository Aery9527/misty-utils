package org.misty.utils.range;

import org.misty.utils.verify.Verifier;

public class BaseRangeBuilder<Self extends BaseRangeBuilder<Self>> {

    private String title = "";

    public BaseRangeBuilder(String title) {
        giveTitle(title);
    }

    public Self giveTitle(String title) {
        Verifier.refuseNull("title", title);
        this.title = title;
        return (Self) this;
    }

    public String getTitle() {
        return title;
    }

}
