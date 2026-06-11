<template>
    <div>
        <el-button @click="edit">编辑</el-button>
        <el-button @click="add">新增</el-button>
  <el-table :data="flatList"  width="1200">
    <el-table-column prop="materialCode" label="物料编码" />
  <el-table-column prop="materialName" label="物料名称" />
  <el-table-column prop="materialType" label="物料类型" />
 <!-- 动态列 -->
  <el-table-column
    v-for="col in dynamicColumns"
    :key="col.code"
    :label="col.label"
  >
    <template #default="{ row }">
      {{ row[col.code] }}
    </template>
  </el-table-column>
  </el-table>
  
     <el-drawer
    v-model="open"
    title="新增物料属性"
    :before-close="handleClose"
    direction="ltr"
    class="demo-drawer"
  >
   <el-tag v-for="tag in dynamicColumns" :key="tag.code" closable :type="tag.label">
      {{ tag.label }}
    </el-tag>


    <div class="demo-drawer__content">
      <el-form :model="form">
        <el-form-item label="编码" label-width="50">
          <el-input v-model="form.code"   />
        </el-form-item>
        <el-form-item label="标签" label-width="50">
          <el-input v-model="form.label"   />
        </el-form-item>
        
      </el-form>
      <div class="demo-drawer__footer">
        <el-button @click="open=false">取消</el-button>
        <el-button type="primary"   @click="onClick">
          提交
        </el-button>
      </div>
    </div>
  </el-drawer>

  
     <el-drawer
    v-model="openMaterial"
    title="新增物料"
    :before-close="handleCloseMaterial"
    direction="ltr"
    class="demo-drawer"
  > 
    <div class="demo-drawer__content">
      <el-form :model="form">
        <el-form-item label="物料编码" label-width="100">
          <el-input v-model="form.materialCode"   />
        </el-form-item>
        <el-form-item label="物料名称" label-width="100">
          <el-input v-model="form.materialName"   />
        </el-form-item>
        <el-form-item label="物料类型" label-width="100">
          <el-input v-model="form.materialType"   />
        </el-form-item>
        
        <el-form-item   v-for="col in dynamicColumns"  :key="col.code" :label="col.label" label-width="100">
          <el-input v-model="form[col.code]"   />
        </el-form-item>
        
        
      </el-form>
      <div class="demo-drawer__footer">
        <el-button @click="openMaterial=false">取消</el-button>
        <el-button type="primary"   @click="handleAdd">提交</el-button>
      </div>
    </div>
  </el-drawer>
     </div>
</template>

<script>
import {  ref,onMounted ,computed} from 'vue'
import { listMaterial,listMaterialMeta,addMaterialMeta,addMaterialInfo  } from '@/api/base/material'
export default{
    data(){
        return {
          openMaterial:false,
            labels:[],
            form:{},
            open:false,
            materialList:[],
            dynamicColumns:[],
            flatList:[],
        }
    },
    created(){
        listMaterialMeta().then(_res=>{
            this.dynamicColumns= _res;
        })
        listMaterial().then(_res=>{
            this.materialList = _res
            this.flatList = this.materialList.map(item => ({...item, ...item.extAttr}))
            // this.generateColumns(_res)
        })
    },
    methods:{
        add(){
          this.openMaterial=true;
        },
        handleCloseMaterial(){
          this.openMaterial=false
        },
        onClick(){
            this.dynamicColumns.push(this.form);
            addMaterialMeta(this.form).then(_res=>{
              this.openMaterial=false
          })
        },
        handleClose(){
            this.open=false
        },
        edit(){
            this.open =true;
        },
        handleAdd(){
          console.log(this.form);
          addMaterialInfo(this.form).then(_res=>{
              this.open=false
          })
        },
        generateColumns(list){
            let keys=[]
        list.forEach(item => {
            Object.keys(item.extAttr || {}).forEach(k => keys.push(k))
        })
         
        }
    }
}
</script>

