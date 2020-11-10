import AppError from '@shared/errors/AppError';

import FakeStorageProvider from '@shared/container/providers/StorageProvider/fakes/FakeStorageProvider';

import FakeCategoriesRepository from '../repositories/fakes/FakeCategoriesRepository';

import UpdateCategoryImageService from './UpdateCategoryImageService';

let fakeCategoriesRepository: FakeCategoriesRepository;

let dakeStorageProvider: FakeStorageProvider;

let updateCategoryImage: UpdateCategoryImageService;

describe('UpdateCategoryImage', () => {
  beforeEach(() => {
    fakeCategoriesRepository = new FakeCategoriesRepository();

    dakeStorageProvider = new FakeStorageProvider();

    updateCategoryImage = new UpdateCategoryImageService(
      fakeCategoriesRepository,
      dakeStorageProvider,
    );
  });

  it('should be able to update the category with an image', async () => {
    const category = await fakeCategoriesRepository.create({
      name: 'Category 01',
    });

    await updateCategoryImage.execute({
      category_id: category.id,
      imageFilename: 'image.jpg',
    });

    await updateCategoryImage.execute({
      category_id: category.id,
      imageFilename: 'image2.jpg',
    });

    expect(category.image).toBe('image2.jpg');
  });

  it('should not be able to update the image from non existing category', async () => {
    await expect(
      updateCategoryImage.execute({
        category_id: 'non existing category id',
        imageFilename: 'image.jpg',
      }),
    ).rejects.toBeInstanceOf(AppError);
  });
});
