import { injectable, inject } from 'tsyringe';

import ICacheProvider from '@shared/container/providers/CacheProvider/models/ICacheProvider';

import Recipe from '../infra/typeorm/entities/Recipe';

import IRecipesRepository from '../repositories/IRecipesRepository';

@injectable()
class ListRecipesService {
  constructor(
    @inject('RecipesRepository')
    private recipesRepository: IRecipesRepository,

    @inject('CacheProvider')
    private cacheProvider: ICacheProvider,
  ) {}

  public async execute(
    search: string,
    page: number,
    category_id: string,
    user_id: string,
  ): Promise<Recipe[]> {
    let recipes = !search
      ? await this.cacheProvider.recover<Recipe[]>(
          `recipes-list:${user_id}:page=${page}:category=${category_id}`,
        )
      : null;

    if (!recipes) {
      recipes = await this.recipesRepository.findAllRecipesByCategoryId(
        search,
        page,
        category_id,
      );

      const recipesLastPage = !search
        ? await this.cacheProvider.recover<Recipe[]>(
            `recipes-list:${user_id}:page=${page - 1}:category=${category_id}`,
          )
        : null;

      if (recipesLastPage) {
        recipes = recipesLastPage.concat(recipes);
      } else if (page > 1 && !search) {
        return [];
      }

      if (!search) {
        await this.cacheProvider.save(
          `recipes-list:${user_id}:page=${page}:category=${category_id}`,
          recipes,
        );
      }
    }

    return recipes;
  }
}

export default ListRecipesService;
