import { provideTransloco } from '@jsverse/transloco';
import {TranslocoHttpLoader} from './transloco.loader';

export function provideTranslocoConfig() {
  return provideTransloco({
    config: {
      availableLangs: ['en', 'fr'],
      defaultLang: 'en',
      fallbackLang: 'en',
      reRenderOnLangChange: true,
      prodMode: false
    },
    loader: TranslocoHttpLoader
  });
}
