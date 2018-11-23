package com.github.sjroom.common;

/**
 * @author huqichao
 * @create 2017-10-24 10:45
 **/
public enum YNStatus implements IResult {

	/**
	 * 有效
	 */
    YES() {
        @Override
        public int getCode() {
            return 1;
        }

        @Override
        public String getMsg() {
            return "有效";
        }
    },
    /**
     * 无效
     */
    NO() {
        @Override
        public int getCode() {
            return 0;
        }

        @Override
        public String getMsg() {
            return "无效";
        }
    };
}
