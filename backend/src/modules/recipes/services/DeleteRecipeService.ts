import { injectable, inject } from 'tsyringe';

import AppError from '@shared/errors/AppError';

import ICacheProvider from '@shared/container/providers/CacheProvider/models/ICacheProvider';

import IRecipesRepository from '../repositories/IRecipesRepository';

@injectable()
class DeleteRecipeService {
  constructor(
    @inject('RecipesRepository')
    private recipesRepository: IRecipesRepository,

    @inject('CacheProvider')
    private cacheProvider: ICacheProvider,
  ) {}

  public async execute(id: string): Promise<void> {
    const recipe = await this.recipesRepository.findById(id);

    if (!recipe) {
      throw new AppError('Recipe not found.');
    }

    this.cacheProvider.invalidatePrefix('recipes-list');

    await this.recipesRepository.remove(recipe);
  }
}

export default DeleteRecipeService;
