package com.kevin.delegationadapter.sample.bean;

import java.util.List;

/**
 * HomeIndex
 *
 * @author zhouwenkai@baidu.com, Created on 2018-04-19 12:16:01
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class HomeIndex {
    public Integer code;
    public String info;
    public String desc;
    public List<Data> data;

    /**
     * Data is the inner class of HomeIndex
     */
    public class Data {
        public List<Header> header;

        /**
         * Header is the inner class of Data
         */
        public class Header {
            public String view_type;
            public String stat;
            public List<Body> body;

            /**
             * Body is the inner class of Header
             */
            public class Body {
                public List<Items> items;

                /**
                 * Items is the inner class of Body
                 */
                public class Items {
                    public String img_url;
                    public String img_url_webp;
                    public List<Action> action;

                    /**
                     * Action is the inner class of Items
                     */
                    public class Action {
                        public String type;
                        public String path;
                        public String extra;
                        public String log_code;
                    }

                    public Integer w;
                    public Integer h;
                    public Integer ad_position_id;
                    public Integer material_id;
                    public String img_url_color;
                }
            }
        }

        public List<Sections> sections;

        /**
         * Sections is the inner class of Data
         */
        public class Sections {
            public String view_type;
            public String stat;
            public List<Body> body;

            /**
             * Body is the inner class of Sections
             */
            public class Body {
                public List<Items> items;

                /**
                 * Items is the inner class of Body
                 */
                public class Items {
                    public String img_url;
                    public String img_url_webp;
                    public List<Action> action;

                    /**
                     * Action is the inner class of Items
                     */
                    public class Action {
                        public String type;
                        public String path;
                        public String extra;
                        public String log_code;
                    }

                    public String product_price;
                    public String product_org_price;
                    public Integer w;
                    public Integer h;
                    public Integer ad_position_id;
                    public Integer material_id;
                    public String img_url_color;
                }
            }
        }

        public List<Festival> festival;

        /**
         * Festival is the inner class of Data
         */
        public class Festival {
            public String view_type;
            public String stat;
            public List<Body> body;

            /**
             * Body is the inner class of Festival
             */
            public class Body {
                public List<Items> items;

                /**
                 * Items is the inner class of Body
                 */
                public class Items {
                    public String img_url;
                    public String img_url_webp;
                    public List<Action> action;

                    /**
                     * Action is the inner class of Items
                     */
                    public class Action {
                        public String type;
                        public String path;
                        public String log_code;
                    }

                    public String img_url_color;
                }

                public String img_url;
                public String img_url_webp;
            }

            public List<Action> action;

            /**
             * Action is the inner class of Festival
             */
            public class Action {
                public String type;
                public String path;
                public String log_code;
            }

            public String block_id;
        }
    }
}
