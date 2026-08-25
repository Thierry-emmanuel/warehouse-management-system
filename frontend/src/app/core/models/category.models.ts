export interface CategorySummary {
  id: number;
  name: string;
  code: string;
  description?: string;
  parentId?: number;
  parentName?: string;
  activeProductsCount?: number;
  subCategories?: CategorySummary[];
}

export interface CreateCategoryRequest {
  name: string;
  code: string;
  description?: string;
  parentId?: number;
}
