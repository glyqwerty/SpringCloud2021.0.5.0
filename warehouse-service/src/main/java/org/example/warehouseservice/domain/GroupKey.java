package org.example.warehouseservice.domain;

import java.util.Objects;

public class GroupKey {
        private final String positionCode; // 库位编码
        private final String materialCode; // 物料编码

        public GroupKey(String positionCode, String materialCode) {
            this.positionCode = positionCode;
            this.materialCode = materialCode;
        }

        // Getter 方法
        public String getPositionCode() { return positionCode; }
        public String getMaterialCode() { return materialCode; }

        // ⚠️ 核心：必须重写 equals 和 hashCode，否则分组会失效！
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GroupKey)) return false;
            GroupKey key = (GroupKey) o;
            return Objects.equals(positionCode, key.positionCode) &&
                    Objects.equals(materialCode, key.materialCode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(positionCode, materialCode);
        }

        // 方便打印查看结果
        @Override
        public String toString() {
            return "库位=" + positionCode + ", 物料=" + materialCode;
        }
    }