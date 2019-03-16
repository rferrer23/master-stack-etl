import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'findLanguageFromKey' })
export class FindLanguageFromKeyPipe implements PipeTransform {
    private languages: any = {
        ca: { name: 'Català' },
        en: { name: 'English' },
        fr: { name: 'Français' },
        gl: { name: 'Galego' },
        it: { name: 'Italiano' },
        'pt-pt': { name: 'Português' },
        es: { name: 'Español' },
        th: { name: 'ไทย' }
        // jhipster-needle-i18n-language-key-pipe - JHipster will add/remove languages in this object
    };
    transform(lang: string): string {
        return this.languages[lang].name;
    }
}
